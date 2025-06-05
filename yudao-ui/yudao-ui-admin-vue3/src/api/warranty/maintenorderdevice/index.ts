import request from '@/config/axios'

// 维修工单设备关联 VO
export interface MaintenOrderDeviceVO {
  workOrderId: number // 维修工单ID
  deviceId: number // 设备ID
  remark: string // 关于此设备在此工单中的备注
}

// 维修工单设备关联 API
export const MaintenOrderDeviceApi = {
  // 查询维修工单设备关联分页
  getMaintenOrderDevicePage: async (params: any) => {
    return await request.get({ url: `/warranty/mainten-order-device/page`, params })
  },

  // 查询维修工单设备关联详情
  getMaintenOrderDevice: async (id: number) => {
    return await request.get({ url: `/warranty/mainten-order-device/get?id=` + id })
  },

  // 新增维修工单设备关联
  createMaintenOrderDevice: async (data: MaintenOrderDeviceVO) => {
    return await request.post({ url: `/warranty/mainten-order-device/create`, data })
  },

  // 修改维修工单设备关联
  updateMaintenOrderDevice: async (data: MaintenOrderDeviceVO) => {
    return await request.put({ url: `/warranty/mainten-order-device/update`, data })
  },

  // 删除维修工单设备关联
  deleteMaintenOrderDevice: async (id: number) => {
    return await request.delete({ url: `/warranty/mainten-order-device/delete?id=` + id })
  },

  // 导出维修工单设备关联 Excel
  exportMaintenOrderDevice: async (params) => {
    return await request.download({ url: `/warranty/mainten-order-device/export-excel`, params })
  },
}