import request from '@/config/axios'

// 部门地址 VO
export interface DeptAddressVO {
  id: number // 地址编号
  deptId: number // 部门id
  addressType: string // 地址类型（例如：办公、仓库）
  streetAddress: string // 街道地址
  city: string // 城市
  stateProvince: string // 省份/州
  postalCode: string // 邮政编码
  country: string // 国家
  isDefault: boolean // 是否默认地址
}

// 部门地址 API
export const DeptAddressApi = {
  // 查询部门地址分页
  getDeptAddressPage: async (params: any) => {
    return await request.get({ url: `/warranty/dept-address/page`, params })
  },

  // 查询部门地址详情
  getDeptAddress: async (id: number) => {
    return await request.get({ url: `/warranty/dept-address/get?id=` + id })
  },

  // 新增部门地址
  createDeptAddress: async (data: DeptAddressVO) => {
    return await request.post({ url: `/warranty/dept-address/create`, data })
  },

  // 修改部门地址
  updateDeptAddress: async (data: DeptAddressVO) => {
    return await request.put({ url: `/warranty/dept-address/update`, data })
  },

  // 删除部门地址
  deleteDeptAddress: async (id: number) => {
    return await request.delete({ url: `/warranty/dept-address/delete?id=` + id })
  },

  // 导出部门地址 Excel
  exportDeptAddress: async (params) => {
    return await request.download({ url: `/warranty/dept-address/export-excel`, params })
  },
}