import request from '@/config/axios'

// 设备 VO
export interface WtyDeviceVO {
  id: number // ID主键
  deviceName: string // 设备名称
  deviceCode: string // 设备编号
  description: string // 设备描述
  type: any // 设备类型
  dept: any // 所属机构
  status: boolean // 设备状态（0：停用，1：启用）
}

// 设备 API
export const WtyDeviceApi = {
  // 查询设备分页
  getWtyDevicePage: async (params: any) => {
    return await request.get({ url: `/warranty/wty-device/page`, params })
  },

  // 查询设备详情
  getWtyDevice: async (id: number) => {
    return await request.get({ url: `/warranty/wty-device/get?id=` + id })
  },

  // 新增设备
  createWtyDevice: async (data: WtyDeviceVO) => {
    return await request.post({ url: `/warranty/wty-device/create`, data })
  },

  // 修改设备
  updateWtyDevice: async (data: WtyDeviceVO) => {
    return await request.put({ url: `/warranty/wty-device/update`, data })
  },

  // 删除设备
  deleteWtyDevice: async (id: number) => {
    return await request.delete({ url: `/warranty/wty-device/delete?id=` + id })
  },

  // 导出设备 Excel
  exportWtyDevice: async (params) => {
    return await request.download({ url: `/warranty/wty-device/export-excel`, params })
  },
}
