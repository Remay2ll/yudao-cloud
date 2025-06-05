<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
       <el-form-item label="配件" prop="componentId">
        <el-select
          v-model="formData.componentId"
          placeholder="请选择配件"
          clearable
          filterable
          :loading="componentLoading"
          class="!w-240px"
        >
          <el-option
            v-for="item in componentList"
            :key="item.id"
            :label="`${item.componentCode} - ${item.componentName}`"
            :value="item.id"
            :title="`${item.componentCode} - ${item.componentName}`"
          >
            <div class="flex items-center">
              <span class="font-medium text-primary">{{ item.componentCode }}</span>
              <el-divider direction="vertical" />
              <span class="text-gray-500">{{ item.componentName }}</span>
            </div>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="配件数量" prop="quantity">
        <el-input v-model="formData.quantity" placeholder="请输入配件数量" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { WtyDeviceTypeApi } from '@/api/warranty/devicetype'
import { WtyComponentApi, WtyComponentVO } from '@/api/warranty/component'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const componentList = ref<WtyComponentVO[]>([]) // 配件列表
const componentLoading = ref(false) // 配件列表加载状态

const formData = ref({
  id: undefined as number | undefined,
  typeId: undefined as number | undefined,
  componentId: undefined as number | undefined,
  quantity: undefined as number | undefined,
})
const formRules = reactive({
  typeId: [{ required: true, message: '设备类型ID不能为空', trigger: 'change' }],
  componentId: [{ required: true, message: '配件ID不能为空', trigger: 'change' }],
})
const formRef = ref() // 表单 Ref

/** 加载配件列表 */
const loadComponentList = async () => {
  componentLoading.value = true
  WtyComponentApi.getWtyComponentList()
    .then(res => {
      if (Array.isArray(res)) {
        componentList.value = res
      } else {
        message.error('获取配件列表失败')
      }
    })
    .catch(() => {
      message.error('获取配件列表失败')
    })
    .finally(() => {
      componentLoading.value = false
    })
}

/** 打开弹窗 */
const open = async (type: string, typeId: number, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  formData.value.typeId = typeId
  // 加载配件列表
  loadComponentList()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const response = await WtyDeviceTypeApi.getWtyTypeComponentRel(id)
      if (response) {
        formData.value = {
          id: response.id,
          typeId: response.typeId,
          componentId: response.componentId,
          quantity: response.quantity
        }
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
    const data = formData.value
    if (formType.value === 'create') {
      await WtyDeviceTypeApi.createWtyTypeComponentRel(data)
      message.success(t('common.createSuccess'))
    } else {
      await WtyDeviceTypeApi.updateWtyTypeComponentRel(data)
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
    typeId: undefined,
    componentId: undefined,
    quantity: undefined,
  }
  formRef.value?.resetFields()
}
</script>