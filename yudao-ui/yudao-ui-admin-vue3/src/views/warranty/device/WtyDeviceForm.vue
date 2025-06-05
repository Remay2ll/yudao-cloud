<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="设备名称" prop="deviceName">
        <el-input v-model="formData.deviceName" placeholder="请输入设备名称" />
      </el-form-item>
      <el-form-item label="设备编号" prop="deviceCode">
        <el-input v-model="formData.deviceCode" placeholder="请输入设备编号" />
      </el-form-item>
      <el-form-item label="设备描述" prop="description">
        <Editor v-model="formData.description" height="150px" />
      </el-form-item>
      <el-form-item label="设备类型" prop="typeId">
        <el-tree-select
          v-model="formData.typeId"
          placeholder="请选择设备类型"
          clearable
          class="w-full"
          :props="{ label: 'typeName', children: 'children' }"
          :load="loadDeviceTypeNode"
          lazy
          check-strictly
          node-key="id"
          :default-expanded-keys="deviceTypeExpandedKeys"
        />
      </el-form-item>
      <el-form-item label="所属机构" prop="deptId">
        <el-tree-select
          v-model="formData.deptId"
          :data="deptTree"
          placeholder="请选择所属机构"
          clearable
          class="w-full"
          :props="{ value: 'id', label: 'name', children: 'children' }"
          node-key="id"
          check-strictly
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getBoolDictOptions(DICT_TYPE.WARRANTY_DEVICE_STATUS)"
            :key="String(dict.value)"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getBoolDictOptions, DICT_TYPE } from '@/utils/dict'
import { WtyDeviceApi, WtyDeviceVO } from '@/api/warranty/device'
import { getSimpleDeptListByType } from '@/api/system/dept'
import { handleTree } from '@/utils/tree'
import { WtyDeviceTypeApi } from '@/api/warranty/devicetype'

/** 设备 表单 */
defineOptions({ name: 'WtyDeviceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  deviceName: undefined,
  deviceCode: undefined,
  description: undefined,
  typeId: undefined,
  deptId: undefined,
  status: undefined,
})
const formRules = reactive({
  deviceName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
  deviceCode: [{ required: true, message: '设备编号不能为空', trigger: 'blur' }],
  typeId: [{ required: true, message: '设备类型ID不能为空', trigger: 'change' }],
  deptId: [{ required: true, message: '所属机构不能为空', trigger: 'change' }],
})
const formRef = ref() // 表单 Ref
const deptTree = ref<any[]>([]) // 新增：用于存储部门树数据
const deviceTypeExpandedKeys = ref<number[]>([]) // 用于存储默认展开的设备类型节点

/** 加载部门树数据 */
const getDeptTreeData = async () => {
  try {
    const data = await getSimpleDeptListByType({ type: 1 })
    deptTree.value = handleTree(data)
  } catch (error) {
    console.error('获取部门树失败', error)
    message.error('获取部门树失败')
    deptTree.value = []
  }
}

/** 获取设备类型的父节点路径 */
async function getDeviceTypePathRecursive(typeId: number, currentPath: number[] = []): Promise<number[]> {
  if (!typeId || typeId === 0) { // Assuming 0 or null/undefined is a root indicator
    return currentPath;
  }
  try {
    const deviceType = await WtyDeviceTypeApi.getWtyDeviceType(typeId);
    if (deviceType && deviceType.parentTypeId && deviceType.parentTypeId !== 0) {
      // Add parent to the path and recurse
      currentPath.unshift(deviceType.parentTypeId);
      return getDeviceTypePathRecursive(deviceType.parentTypeId, currentPath);
    }
    return currentPath; // Return current path if no valid parentTypeId or at root
  } catch (error) {
    console.error(`Error fetching device type ${typeId} for path calculation:`, error);
    return currentPath; // Return what we have so far in case of an error
  }
}

/** 加载设备类型树节点数据 */
const loadDeviceTypeNode = async (node, resolve) => {
  try {
    // 如果是根节点，则加载顶级父类型
    const parentIds = node.level === 0 ? [] : [node.data.id]
    // 构建查询参数
    const params = {
      parentTypeIds: parentIds
    }
    // 加载设备类型树形数据
    WtyDeviceTypeApi.getWtyDeviceTypeTreeOneLevel(params).then((res) => {
      // 解析数据
      const nodes = res?.map((item) => ({
        id: item.id,
        typeName: item.typeName,
        // 如果 API 返回的 item 包含 children 属性 (即使是空数组), 
        // el-tree-select 会用它来判断是否是叶子节点。
        // 如果 API 返回的 item 不包含 children 属性，
        // lazy load 会尝试为这个节点加载子节点，除非明确设置 leaf: true。
        // 根据 index.vue 的实现，我们先不设置 leaf，依赖 API 返回结构和 el-tree-select 的默认行为。
        // 如果 getWtyDeviceTypeTreeOneLevel 确保返回的节点如果没有子节点，就不包含 children 属性，
        // 或者 children 为空数组，那么这个配置是合适的。
        // 如果 getWtyDeviceTypeTreeOneLevel 返回的节点没有 children 属性但实际上是叶子节点，
        // 那么可能需要在这里添加 leaf: true
        ...(item.children && { children: item.children }), // 如果有children，则传递
        // 如果需要显式标记叶子节点（例如API不返回children，但你知道它是叶子），则添加：
        // leaf: !item.hasChildren // (假设API提供hasChildren字段)
        // 或者 leaf: true // (如果getWtyDeviceTypeTreeOneLevel获取的总是叶子节点或无子节点的节点)
      }))
      resolve(nodes || []) // 确保在res为null或undefined时传递空数组
    })
  } catch (error) {
    console.error('加载设备类型树形数据失败', error)
    resolve([])
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 加载部门树
  await getDeptTreeData()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await WtyDeviceApi.getWtyDevice(id)
      // 如果是修改模式并且有 typeId，则获取并设置展开路径
      if (formData.value.typeId) {
        deviceTypeExpandedKeys.value = await getDeviceTypePathRecursive(formData.value.typeId);
      }
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as WtyDeviceVO
    if (formType.value === 'create') {
      await WtyDeviceApi.createWtyDevice(data)
      message.success(t('common.createSuccess'))
    } else {
      await WtyDeviceApi.updateWtyDevice(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    deviceName: undefined,
    deviceCode: undefined,
    description: undefined,
    typeId: undefined,
    deptId: undefined,
    status: undefined,
  }
  formRef.value?.resetFields()
  deviceTypeExpandedKeys.value = [] // 重置时清空
}
</script>