import request from '@/config/axios'

// 故障类型 VO
export interface IssueTypeVO {
  id: number // ID主键
  typeCode: string // 故障类型编码
  typeName: string // 故障类型名称
  description: string // 故障类型描述
  parentId: number // 父级故障类型ID（0表示顶级分类）
  priorityLevel: number // 默认优先级（1-高，2-中，3-低）
  expectedDuration: number // 预计处理时长（分钟）
  sort: number // 显示顺序
  status: boolean // 状态（0：停用，1：启用）
}

// 故障类型 API
export const IssueTypeApi = {
  // 查询故障类型列表
  getIssueTypeList: async (params) => {
    return await request.get({ url: `/warranty/issue-type/list`, params })
  },

  // 查询故障类型详情
  getIssueType: async (id: number) => {
    return await request.get({ url: `/warranty/issue-type/get?id=` + id })
  },

  // 新增故障类型
  createIssueType: async (data: IssueTypeVO) => {
    return await request.post({ url: `/warranty/issue-type/create`, data })
  },

  // 修改故障类型
  updateIssueType: async (data: IssueTypeVO) => {
    return await request.put({ url: `/warranty/issue-type/update`, data })
  },

  // 删除故障类型
  deleteIssueType: async (id: number) => {
    return await request.delete({ url: `/warranty/issue-type/delete?id=` + id })
  },

  // 导出故障类型 Excel
  exportIssueType: async (params) => {
    return await request.download({ url: `/warranty/issue-type/export-excel`, params })
  },
}