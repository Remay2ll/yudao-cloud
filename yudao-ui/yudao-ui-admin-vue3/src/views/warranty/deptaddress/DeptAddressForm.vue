<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="部门id" prop="deptId">
        <el-input v-model="formData.deptId" placeholder="请输入部门id" />
      </el-form-item>
      <el-form-item label="地址类型（例如：办公、仓库）" prop="addressType">
        <el-select v-model="formData.addressType" placeholder="请选择地址类型（例如：办公、仓库）">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="街道地址" prop="streetAddress">
        <el-input v-model="formData.streetAddress" placeholder="请输入街道地址" />
      </el-form-item>
      <el-form-item label="城市" prop="city">
        <el-input v-model="formData.city" placeholder="请输入城市" />
      </el-form-item>
      <el-form-item label="省份/州" prop="stateProvince">
        <el-input v-model="formData.stateProvince" placeholder="请输入省份/州" />
      </el-form-item>
      <el-form-item label="邮政编码" prop="postalCode">
        <el-input v-model="formData.postalCode" placeholder="请输入邮政编码" />
      </el-form-item>
      <el-form-item label="国家" prop="country">
        <el-input v-model="formData.country" placeholder="请输入国家" />
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
import { DeptAddressApi, DeptAddressVO } from '@/api/warranty/deptaddress'
import { getBoolDictOptions, DICT_TYPE } from '@/utils/dict'

/** 部门地址 表单 */
defineOptions({ name: 'DeptAddressForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  deptId: undefined,
  addressType: undefined,
  streetAddress: undefined,
  city: undefined,
  stateProvince: undefined,
  postalCode: undefined,
  country: undefined,
  isDefault: false,
})
const formRules = reactive({
  deptId: [{ required: true, message: '部门id不能为空', trigger: 'blur' }],
  streetAddress: [{ required: true, message: '街道地址不能为空', trigger: 'blur' }],
  isDefault: [{ required: true, message: '是否默认地址不能为空', trigger: 'blur' }],
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
      const data = await DeptAddressApi.getDeptAddress(id)
      formData.value = {
        ...data,
        // 确保 isDefault 字段是布尔类型，以匹配 radio 组件的期望值
        isDefault: data.isDefault === true || data.isDefault === 'true'
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
    const data = formData.value as unknown as DeptAddressVO
    if (formType.value === 'create') {
      await DeptAddressApi.createDeptAddress(data)
      message.success(t('common.createSuccess'))
    } else {
      await DeptAddressApi.updateDeptAddress(data)
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
    deptId: undefined,
    addressType: undefined,
    streetAddress: undefined,
    city: undefined,
    stateProvince: undefined,
    postalCode: undefined,
    country: undefined,
    isDefault: false,
  }
  formRef.value?.resetFields()
}
</script>