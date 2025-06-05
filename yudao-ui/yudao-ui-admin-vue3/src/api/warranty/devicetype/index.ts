import request from '@/config/axios'

// 设备类型 VO
export interface WtyDeviceTypeVO {
  id: number // 主键ID
  typeCode: string // 类型编码
  typeName: string // 类型名称
  parentTypeId: number // 父类型ID
  description: string // 类型描述
  status: number // 状态
}

// 设备类型 API
export const WtyDeviceTypeApi = {
  // 查询设备类型分页
  getWtyDeviceTypePage: async (params: any) => {
    return await request.get({ url: `/warranty/wty-device-type/page`, params })
  },

  // 查询设备类型详情
  getWtyDeviceType: async (id: number) => {
    return await request.get({ url: `/warranty/wty-device-type/get?id=` + id })
  },

  // 新增设备类型
  createWtyDeviceType: async (data: WtyDeviceTypeVO) => {
    return await request.post({ url: `/warranty/wty-device-type/create`, data })
  },

  // 修改设备类型
  updateWtyDeviceType: async (data: WtyDeviceTypeVO) => {
    return await request.put({ url: `/warranty/wty-device-type/update`, data })
  },

  // 删除设备类型
  deleteWtyDeviceType: async (id: number) => {
    return await request.delete({ url: `/warranty/wty-device-type/delete?id=` + id })
  },

  // 导出设备类型 Excel
  exportWtyDeviceType: async (params) => {
    return await request.download({ url: `/warranty/wty-device-type/export-excel`, params })
  },

  // 查询设备类型树
  getWtyDeviceTypeTree: async (params) => {
    return await request.get({ url: `/warranty/wty-device-type/tree`, params })
  },

  // 查询单层设备类型树
  getWtyDeviceTypeTreeOneLevel: async (params) => {
    return await request.get({ url: `/warranty/wty-device-type/tree-one-level`, params })
  },

  // ==================== 子表（设备类型-配件关联） ====================

  // 获得设备类型-配件关联分页
  getWtyTypeComponentRelPage: async (params) => {
    return await request.get({
      url: `/warranty/wty-device-type/wty-type-component-rel/page`,
      params
    })
  },
  // 新增设备类型-配件关联
  createWtyTypeComponentRel: async (data) => {
    return await request.post({
      url: `/warranty/wty-device-type/wty-type-component-rel/create`,
      data
    })
  },

  // 修改设备类型-配件关联
  updateWtyTypeComponentRel: async (data) => {
    return await request.put({
      url: `/warranty/wty-device-type/wty-type-component-rel/update`,
      data
    })
  },

  // 删除设备类型-配件关联
  deleteWtyTypeComponentRel: async (id: number) => {
    return await request.delete({
      url: `/warranty/wty-device-type/wty-type-component-rel/delete?id=` + id
    })
  },

  // 获得设备类型-配件关联
  getWtyTypeComponentRel: async (id: number) => {
    return await request.get({
      url: `/warranty/wty-device-type/wty-type-component-rel/get?id=` + id
    })
  }
}
