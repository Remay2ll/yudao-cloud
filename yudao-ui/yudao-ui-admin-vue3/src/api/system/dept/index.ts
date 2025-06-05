import request from '@/config/axios'

export interface DeptVO {
  id?: number
  name: string
  parentId: number
  status: number
  sort: number
  leaderUserId: number
  phone: string
  email: string
  createTime: Date
  type?: number
}

// ========== 新增开始 ==========
export interface DeptListReqVO {
  name?: string
  status?: number
  type?: number
}

export interface DeptSimpleRespVO {
  id: number // 和后端统一，改为 number，若后端确实是 Long，前端通常处理为 number 或 string
  name: string
  parentId: number // 和后端统一，改为 number
}
// ========== 新增结束 ==========

// 查询部门（精简)列表
export const getSimpleDeptList = async (): Promise<DeptVO[]> => {
  return await request.get({ url: '/system/dept/simple-list' })
}

// ========== 新增开始 ==========
// 查询部门精简信息列表（可按类型筛选）
export const getSimpleDeptListByType = async (params: DeptListReqVO): Promise<DeptSimpleRespVO[]> => {
  return await request.get({ url: '/system/dept/simple-list-by-type', params })
}
// ========== 新增结束 ==========

// 查询部门列表
export const getDeptPage = async (params: PageParam) => {
  return await request.get({ url: '/system/dept/list', params })
}

// 查询部门详情
export const getDept = async (id: number) => {
  return await request.get({ url: '/system/dept/get?id=' + id })
}

// 新增部门
export const createDept = async (data: DeptVO) => {
  return await request.post({ url: '/system/dept/create', data: data })
}

// 修改部门
export const updateDept = async (params: DeptVO) => {
  return await request.put({ url: '/system/dept/update', data: params })
}

// 删除部门
export const deleteDept = async (id: number) => {
  return await request.delete({ url: '/system/dept/delete?id=' + id })
}
