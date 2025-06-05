import request from '@/config/axios'

// 工单配件 VO
export interface MaintenOrderPartVO {
  workOrderId: number // 维修工单ID
  partId: number // 配件ID
  quantity: number // 数量
  remark: string // 关于此配件在此工单中的备注
}

// 工单配件 API
export const MaintenOrderPartApi = {
  // 查询工单配件分页
  getMaintenOrderPartPage: async (params: any) => {
    return await request.get({ url: `/warranty/mainten-order-part/page`, params })
  },

  // 查询工单配件详情
  getMaintenOrderPart: async (id: number) => {
    return await request.get({ url: `/warranty/mainten-order-part/get?id=` + id })
  },

  // 新增工单配件
  createMaintenOrderPart: async (data: MaintenOrderPartVO) => {
    return await request.post({ url: `/warranty/mainten-order-part/create`, data })
  },

  // 修改工单配件
  updateMaintenOrderPart: async (data: MaintenOrderPartVO) => {
    return await request.put({ url: `/warranty/mainten-order-part/update`, data })
  },

  // 删除工单配件
  deleteMaintenOrderPart: async (id: number) => {
    return await request.delete({ url: `/warranty/mainten-order-part/delete?id=` + id })
  },

  // 导出工单配件 Excel
  exportMaintenOrderPart: async (params) => {
    return await request.download({ url: `/warranty/mainten-order-part/export-excel`, params })
  },
}