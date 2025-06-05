import request from '@/config/axios'

// 配件 VO
export interface WtyComponentVO {
  id: number // 主键ID
  componentCode: string // 配件编号
  componentName: string // 配件名称
  unit: string // 单位
  remark: string // 备注
  priceMaintain: number // 配件维修价格
  priceChange: number // 配件更换价格
  status: boolean // 设备状态
}

// 配件 API
export const WtyComponentApi = {
  // 查询配件分页
  getWtyComponentPage: async (params: any) => {
    return await request.get({ url: `/warranty/wty-component/page`, params })
  },

  // 查询配件详情
  getWtyComponent: async (id: number) => {
    return await request.get({ url: `/warranty/wty-component/get?id=` + id })
  },

  // 新增配件
  createWtyComponent: async (data: WtyComponentVO) => {
    return await request.post({ url: `/warranty/wty-component/create`, data })
  },

  // 修改配件
  updateWtyComponent: async (data: WtyComponentVO) => {
    return await request.put({ url: `/warranty/wty-component/update`, data })
  },

  // 删除配件
  deleteWtyComponent: async (id: number) => {
    return await request.delete({ url: `/warranty/wty-component/delete?id=` + id })
  },

  // 导出配件 Excel
  exportWtyComponent: async (params) => {
    return await request.download({ url: `/warranty/wty-component/export-excel`, params })
  },

  // 获得配件列表
  getWtyComponentList: async () => {
    return await request.get({ url: `/warranty/wty-component/list` }) 
  },
}
