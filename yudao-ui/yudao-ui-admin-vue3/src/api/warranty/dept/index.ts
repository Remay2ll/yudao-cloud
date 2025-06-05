import request from '@/config/axios'

// 部门 VO
export interface DeptVO {
  id: number // 部门id
  name: string // 部门名称
  parentId: number // 父部门id
  leaderUserId: number // 负责人
  status: number // 部门状态（0正常 1停用）
  createTime: string[] // 创建时间
}

// 部门 API
export const DeptApi = {
  // 查询部门分页
  getDeptPage: async (params: any) => {
    return await request.get({ url: `/warranty/dept/page`, params })
  },

  // 查询部门详情
  getDept: async (id: number) => {
    return await request.get({ url: `/warranty/dept/get?id=` + id })
  },

  // 新增部门
  createDept: async (data: DeptVO) => {
    return await request.post({ url: `/warranty/dept/create`, data })
  },

  // 修改部门
  updateDept: async (data: DeptVO) => {
    return await request.put({ url: `/warranty/dept/update`, data })
  },

  // 删除部门
  deleteDept: async (id: number) => {
    return await request.delete({ url: `/warranty/dept/delete?id=` + id })
  },

  // 导出部门 Excel
  exportDept: async (params) => {
    return await request.download({ url: `/warranty/dept/export-excel`, params })
  },

// ==================== 子表（部门地址） ====================

  // 获得部门地址分页
  getDeptAddressPage: async (params) => {
    return await request.get({ url: `/warranty/dept/dept-address/page`, params })
  },
  // 新增部门地址
  createDeptAddress: async (data) => {
    return await request.post({ url: `/warranty/dept/dept-address/create`, data })
  },

  // 修改部门地址
  updateDeptAddress: async (data) => {
    return await request.put({ url: `/warranty/dept/dept-address/update`, data })
  },

  // 删除部门地址
  deleteDeptAddress: async (id: number) => {
    return await request.delete({ url: `/warranty/dept/dept-address/delete?id=` + id })
  },

  // 获得部门地址
  getDeptAddress: async (id: number) => {
    return await request.get({ url: `/warranty/dept/dept-address/get?id=` + id })
  },
}
