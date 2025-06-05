import request from '@/config/axios'

// 维修工单 VO
export interface MaintenOrderVO {
  id?: number // 主键ID
  workOrderNo?: string // 工单编号
  title?: string // 工单标题/简述
  orderType?: number // 工单类型 (例如 0:计划内, 1:紧急, 2:巡检)
  orderStatus?: number // 工单状态 (例如 0:待派发, 1:已派发, 2:已接单, 3:进行中, 4:已完工, 5:已取消, 6:已拒绝)
  priority?: number // 优先级 (例如 0:低, 1:中, 2:高)
  agreementNo?: string // 关联维保协议号
  customerId?: number // 客户ID
  customerName?: string // 客户名称
  customerContactName?: string // 客户联系人
  customerContactPhone?: string // 客户联系电话
  customerAddress?: string // 客户地址/报修地址
  problemDescription?: string // 问题描述/维保前描述
  reportTime?: Date // 报修时间
  reportedByUserId?: number // 报修人用户ID
  maintenanceCompanyId?: number // 负责维保公司ID
  assignedToUserId?: number // 指派维修工用户ID
  assignedToUserName?: string // 指派维修工姓名
  assignmentTime?: Date // 分派时间
  acceptanceTime?: Date // 接单时间
  scheduledStartTime?: Date // 计划开始时间
  scheduledEndTime?: Date // 计划结束时间
  actualStartTime?: Date // 实际开始时间/维保开始时间
  actualEndTime?: Date // 实际完成时间/完工时间
  checkInLocation?: string // 签到地址
  checkOutLocation?: string // 签退地址
  workSummary?: string // 工作总结/维保后描述
  filesBeforeMaintenance?: string // 维保前文件
  filesAfterMaintenance?: string // 维保后文件
  technicianSignatureFile?: string // 维修员工签字文件
  customerSignatureFile?: string // 客户签字文件
  refuseTime?: Date // 拒绝时间 (维修工拒绝)
  refuseReason?: string // 拒绝原因
  cancelTime?: Date // 取消时间
  cancelReason?: string // 取消原因
  cancelledByUserId?: number // 取消操作人用户ID
  remark?: string // 备注
  owningDeptId?: number // 设备所属单位ID
  serviceAreaDeptId?: number // 服务片区部门ID
  issueTypeId?: number // 故障类型ID
  issueTypeName?: string // 故障类型名称
  estimatedDuration?: number // 预计处理时长(分钟)
  maintenOrderDevices?: MaintenOrderDeviceVO[] // 维修工单设备关联列表
  maintenOrderParts?: MaintenOrderPartVO[] // 工单配件列表
  createTime?: Date // 创建时间
}

// 维修工单设备关联 VO
export interface MaintenOrderDeviceVO {
  id?: number
  workOrderId?: number // 维修工单ID
  deviceId?: number // 设备ID
  remark?: string // 关于此设备在此工单中的备注
  createTime?: Date
  updateTime?: Date
  creator?: string
  updater?: string
  deleted?: boolean
}

// 工单配件 VO
export interface MaintenOrderPartVO {
  id?: number
  workOrderId?: number // 维修工单ID
  partId?: number // 配件ID
  quantity?: number // 数量
  remark?: string // 关于此配件在此工单中的备注
  createTime?: Date
  updateTime?: Date
  creator?: string
  updater?: string
  deleted?: boolean
}

// 维修工单 API
export const MaintenOrderApi = {
  // 查询维修工单分页
  getMaintenOrderPage: async (params: any) => {
    return await request.get({ url: `/warranty/mainten-order/page`, params })
  },

  // 查询维修工单详情
  getMaintenOrder: async (id: number) => {
    return await request.get({ url: `/warranty/mainten-order/get?id=` + id })
  },

  // 新增维修工单
  createMaintenOrder: async (data: MaintenOrderVO) => {
    return await request.post({ url: `/warranty/mainten-order/create`, data })
  },

  // 修改维修工单
  updateMaintenOrder: async (data: MaintenOrderVO) => {
    return await request.put({ url: `/warranty/mainten-order/update`, data })
  },

  // 删除维修工单
  deleteMaintenOrder: async (id: number) => {
    return await request.delete({ url: `/warranty/mainten-order/delete?id=` + id })
  },

  // 导出维修工单 Excel
  exportMaintenOrder: async (params) => {
    return await request.download({ url: `/warranty/mainten-order/export-excel`, params })
  },

// ==================== 子表（维修工单设备关联） ====================

  // 获得维修工单设备关联列表
  getMaintenOrderDeviceListByWorkOrderId: async (workOrderId) => {
    return await request.get({ url: `/warranty/mainten-order/mainten-order-device/list-by-work-order-id?workOrderId=` + workOrderId })
  },

// ==================== 子表（工单配件） ====================

  // 获得工单配件列表
  getMaintenOrderPartListByWorkOrderId: async (workOrderId) => {
    return await request.get({ url: `/warranty/mainten-order/mainten-order-part/list-by-work-order-id?workOrderId=` + workOrderId })
  },
}
