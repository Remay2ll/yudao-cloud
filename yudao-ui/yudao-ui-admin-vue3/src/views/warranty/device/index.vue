<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备编号" prop="deviceCode">
        <el-input
          v-model="queryParams.deviceCode"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="typeId" label-width="auto">
        <el-tree-select
          v-model="queryParams.typeId"
          placeholder="请选择类型"
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
      <el-form-item label="所属机构" prop="deptId" label-width="auto">
        <el-tree-select
          v-model="queryParams.deptId"
          :data="deptList"
          placeholder="请选择所属机构"
          clearable
          class="!w-240px"
          :props="{ value: 'id', label: 'name', children: 'children' }"
          node-key="id"
          check-strictly
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择设备状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getBoolDictOptions(DICT_TYPE.WARRANTY_DEVICE_STATUS)"
            :key="dict.label"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否审核" prop="isAudit">
        <el-select
          v-model="queryParams.isAudit"
          placeholder="请选择是否审核"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.WARRANTY_AUDIT)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['warranty:wty-device:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['warranty:wty-device:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column type="index" width="50" />
      <el-table-column label="设备编号" align="center" prop="deviceCode" />
      <el-table-column label="设备名称" align="center" prop="deviceName" />
      <el-table-column label="设备类型" align="center" prop="typeName" />
      <el-table-column label="设备描述" align="center" prop="description">
        <template #default="scope">
          <span v-if="scope.row.description">
            {{ scope.row.description.replace(/<[^>]+>/g, '') }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="所属机构" align="center" prop="deptName" />
      <el-table-column label="设备状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.WARRANTY_DEVICE_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="是否审核" align="center" prop="isAudit">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.WARRANTY_AUDIT" :value="scope.row.isAudit" />
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['warranty:wty-device:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['warranty:wty-device:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <WtyDeviceForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions,getBoolDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { WtyDeviceApi, WtyDeviceVO } from '@/api/warranty/device'
import WtyDeviceForm from './WtyDeviceForm.vue'
import { WtyDeviceTypeApi } from '@/api/warranty/devicetype'
import { getSimpleDeptListByType } from '@/api/system/dept'
import { handleTree } from '@/utils/tree'

/** 设备 列表 */
defineOptions({ name: 'WtyDevice' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<WtyDeviceVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  deviceName: undefined,
  deviceCode: undefined,
  description: undefined,
  typeId: undefined,
  deptId: undefined,
  status: undefined,
  isAudit: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const deptList = ref<any[]>([]) // 修改类型以适应树形结构，或者使用 Tree[] 如果 Tree 类型已导入且兼容

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await WtyDeviceApi.getWtyDevicePage(queryParams)
    // 处理设备描述内容
    data.list = data.list.map(item => {
      if (item.description) {
        const text = item.description.replace(/<[^>]+>/g, '').replace(/\s+/g, '')
        if (!text) {
          item.description = ''
        }
      }
      return item
    })
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 获取部门列表 */
const getDeptList = async () => {
  try {
    const data = await getSimpleDeptListByType({ type: 1 }) // 调用API，并设置type为1
    deptList.value = handleTree(data) // 将获取的列表数据转换为树形结构
  } catch (error) {
    console.error('获取部门列表失败', error)
    message.error('获取部门列表失败')
    deptList.value = [] // 出错时清空列表
  }
}

/** 加载树节点数据 */
let nodes = []
const loadNode = async (node, resolve) => {
  try {
    // 如果是根节点，则加载顶级父类型
    const parentIds = node.level === 0 ? [] : [node.data.id]
    // 构建查询参数
    const params = {
      parentTypeIds: parentIds
    }
    // 加载设备类型树形数据
    WtyDeviceTypeApi.getWtyDeviceTypeTreeOneLevel(params).then((res) => {
      // 解析数据并使用后端返回的 isLeaf 字段标记是否为叶子节点
      nodes = res?.map((item) => ({
        id: item.id,
        typeCode: item.typeCode,
        typeName: item.typeName,
        parentTypeId: item.parentTypeId
      }))
      resolve(nodes)
    })
  } catch (error) {
    console.error('加载设备类型树形数据失败', error)
    resolve([])
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await WtyDeviceApi.deleteWtyDevice(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await WtyDeviceApi.exportWtyDevice(queryParams)
    download.excel(data, '设备.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
  getDeptList()
})
</script>