<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="类型编码" prop="typeCode">
        <el-input v-model="formData.typeCode" placeholder="请输入类型编码" />
      </el-form-item>
      <el-form-item label="类型名称" prop="typeName">
        <el-input v-model="formData.typeName" placeholder="请输入类型名称" />
      </el-form-item>
      <el-form-item label="父类型" prop="parentTypeId">
        <el-tree-select
          v-model="formData.parentTypeId"
          placeholder="请选择父类型"
          clearable
          class="!w-240px"
          :props="{
            label: 'typeName',
            children: 'children'
          }"
          :load="loadNode"
          lazy
          check-strictly
          node-key="id"
        />
      </el-form-item>
      <el-form-item label="类型描述" prop="description">
        <Editor v-model="formData.description" height="150px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.WARRANTY_DEVICE_TYPE_STATUS)"
            :key="dict.value"
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
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { WtyDeviceTypeApi, WtyDeviceTypeVO } from '@/api/warranty/devicetype'

/** 设备类型 表单 */
defineOptions({ name: 'WtyDeviceTypeForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改

/** 加载树节点数据 */
const loadNode = async (node, resolve) => {
  try {
    // 如果是根节点，则加载顶级父类型
    const parentIds = node.level === 0 ? [] : [node.data.id]
    // 构建查询参数
    const params = {
      parentTypeIds: parentIds
    }
    console.log('params', params)
    // 加载设备类型树形数据
    WtyDeviceTypeApi.getWtyDeviceTypeTreeOneLevel(params).then((res) => {
      // 解析数据并使用后端返回的 isLeaf 字段标记是否为叶子节点
      const nodes = res?.map((item) => ({
        id: item.id,
        typeCode: item.typeCode,
        typeName: item.typeName,
        parentTypeId: item.parentTypeId,
      }))
      resolve(nodes)
    })
  } catch (error) {
    console.error('加载设备类型树形数据失败', error)
    resolve([])
  }
}

const formData = ref({
  id: undefined,
  typeCode: undefined,
  typeName: undefined,
  parentTypeId: undefined,
  description: undefined,
  status: undefined,
})
const formRules = reactive({
  typeCode: [{ required: true, message: '类型编码不能为空', trigger: 'blur' }],
  typeName: [{ required: true, message: '类型名称不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await WtyDeviceTypeApi.getWtyDeviceType(id)
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
    const data = formData.value as unknown as WtyDeviceTypeVO
    if (formType.value === 'create') {
      await WtyDeviceTypeApi.createWtyDeviceType(data)
      message.success(t('common.createSuccess'))
    } else {
      await WtyDeviceTypeApi.updateWtyDeviceType(data)
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
    typeCode: undefined,
    typeName: undefined,
    parentTypeId: undefined,
    description: undefined,
    status: undefined,
  }
  formRef.value?.resetFields()
}
</script>