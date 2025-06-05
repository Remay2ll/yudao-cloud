<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="故障类型编码" prop="typeCode">
        <el-input v-model="formData.typeCode" placeholder="请输入故障类型编码" />
      </el-form-item>
      <el-form-item label="故障类型名称" prop="typeName">
        <el-input v-model="formData.typeName" placeholder="请输入故障类型名称" />
      </el-form-item>
      <el-form-item label="故障类型描述" prop="description">
        <Editor v-model="formData.description" height="150px" />
      </el-form-item>
      <el-form-item label="父级故障类型ID（0表示顶级分类）" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="issueTypeTree"
          :props="{...defaultProps, label: 'typeName'}"
          check-strictly
          default-expand-all
          placeholder="请选择父级故障类型ID（0表示顶级分类）"
        />
      </el-form-item>
      <el-form-item label="默认优先级（1-高，2-中，3-低）" prop="priorityLevel">
        <el-input v-model="formData.priorityLevel" placeholder="请输入默认优先级（1-高，2-中，3-低）" />
      </el-form-item>
      <el-form-item label="预计处理时长（分钟）" prop="expectedDuration">
        <el-input v-model="formData.expectedDuration" placeholder="请输入预计处理时长（分钟）" />
      </el-form-item>
      <el-form-item label="显示顺序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入显示顺序" />
      </el-form-item>
      <el-form-item label="状态（0：停用，1：启用）" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio value="1">请选择字典生成</el-radio>
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
import { IssueTypeApi, IssueTypeVO } from '@/api/warranty/issuetype'
import { defaultProps, handleTree } from '@/utils/tree'

/** 故障类型 表单 */
defineOptions({ name: 'IssueTypeForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  typeCode: undefined,
  typeName: undefined,
  description: undefined,
  parentId: undefined,
  priorityLevel: undefined,
  expectedDuration: undefined,
  sort: undefined,
  status: undefined,
})
const formRules = reactive({
  typeCode: [{ required: true, message: '故障类型编码不能为空', trigger: 'blur' }],
  typeName: [{ required: true, message: '故障类型名称不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const issueTypeTree = ref() // 树形结构

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
      formData.value = await IssueTypeApi.getIssueType(id)
    } finally {
      formLoading.value = false
    }
  }
  await getIssueTypeTree()
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
    const data = formData.value as unknown as IssueTypeVO
    if (formType.value === 'create') {
      await IssueTypeApi.createIssueType(data)
      message.success(t('common.createSuccess'))
    } else {
      await IssueTypeApi.updateIssueType(data)
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
    description: undefined,
    parentId: undefined,
    priorityLevel: undefined,
    expectedDuration: undefined,
    sort: undefined,
    status: undefined,
  }
  formRef.value?.resetFields()
}

/** 获得故障类型树 */
const getIssueTypeTree = async () => {
  issueTypeTree.value = []
  const data = await IssueTypeApi.getIssueTypeList()
  const root: Tree = { id: 0, name: '顶级故障类型', children: [] }
  root.children = handleTree(data, 'id', 'parentId')
  issueTypeTree.value.push(root)
}
</script>