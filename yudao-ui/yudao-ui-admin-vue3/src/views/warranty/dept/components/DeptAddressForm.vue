<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="110px"
      v-loading="formLoading"
    >
       <el-form-item label="地址类型" prop="addressType">
        <el-select v-model="formData.addressType" placeholder="请选择地址类型">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.WARRANTY_DEPT_ADDRESS_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所在地区" prop="areaId">
        <el-cascader
          v-model="formData.areaId"
          :options="areaList"
          :props="cascaderProps"
          class="w-full"
          clearable
          filterable
          placeholder="请选择所在地区"
        />
      </el-form-item>
      <el-form-item label="街道地址" prop="streetAddress">
        <el-input v-model="formData.streetAddress" placeholder="请输入街道地址" />
      </el-form-item>
      <el-form-item label="邮政编码" prop="postalCode">
        <el-input v-model="formData.postalCode" placeholder="请输入邮政编码" />
      </el-form-item>
      <el-form-item label="是否默认地址" prop="isDefault">
        <el-radio-group v-model="formData.isDefault">
          <el-radio
            v-for="dict in getBoolDictOptions(DICT_TYPE.INFRA_BOOLEAN_STRING)"
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
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { DeptApi } from '@/api/warranty/dept'
import { getAreaTree, type AreaNodeRespVO } from '@/api/system/area'
import { getStrDictOptions, getBoolDictOptions, DICT_TYPE } from '@/utils/dict'

const { t } = useI18n()
const message = ElMessage

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')

const formData = ref<any>({
  id: undefined,
  deptId: undefined,
  addressType: undefined,
  streetAddress: undefined,
  areaId: undefined,
  postalCode: undefined,
  isDefault: false
})
const formRules = reactive({
  deptId: [{ required: true, message: '部门id不能为空', trigger: 'blur' }],
  addressType: [{ required: true, message: '地址类型不能为空', trigger: 'blur' }],
  areaId: [{ required: true, message: '请选择所在地区', trigger: 'change' }],
  streetAddress: [{ required: true, message: '街道地址不能为空', trigger: 'blur' }],
  postalCode: [{ required: false, message: '邮政编码不能为空', trigger: 'blur' }],
  isDefault: [{ required: true, message: '是否默认地址不能为空', trigger: 'blur' }]
})
const formRef = ref()
const areaList = ref<AreaNodeRespVO[]>([])
const cascaderProps = {
  value: 'id',
  label: 'name',
  children: 'children',
  emitPath: false
}

/** 打开弹窗 */
const open = async (type: string, id?: number, deptId?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (deptId) {
    formData.value.deptId = deptId
  }
  if (id) {
    formLoading.value = true
    try {
      const data = await DeptApi.getDeptAddress(id)
      formData.value = {
        ...data,
        // 确保 isDefault 字段是布尔类型，以匹配 radio 组件的期望值
        isDefault: data.isDefault === true || data.isDefault === 'true'
      }
    } finally {
      formLoading.value = false
    }
  }
  if (areaList.value.length === 0) {
    await loadAreaList()
  }
}
defineExpose({ open })

/** 提交表单 */
const emit = defineEmits(['success'])
const submitForm = async () => {
  await formRef.value.validate()
  formLoading.value = true
  try {
    const data = formData.value

    if (formType.value === 'create') {
      await DeptApi.createDeptAddress(data)
      message.success(t('common.createSuccess'))
    } else {
      await DeptApi.updateDeptAddress(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    deptId: formData.value.deptId,
    addressType: undefined,
    streetAddress: undefined,
    areaId: undefined,
    postalCode: undefined,
    isDefault: false
  }
  formRef.value?.resetFields()
}

/** 加载地区列表 */
const loadAreaList = async () => {
  try {
    const data = await getAreaTree()
    areaList.value = data
  } catch (error) {
    message.error('获取地区数据失败')
    console.error('获取地区数据失败:', error)
  }
}

/** 初始化 */
// onMounted(() => {
  // 之前决定在弹窗打开时加载
// })
</script>