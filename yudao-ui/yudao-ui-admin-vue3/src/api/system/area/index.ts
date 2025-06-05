// export * from './types' // 移除类型文件的导出
import request from '@/config/axios'

// 新增：直接在此处定义和导出 AreaNodeRespVO 类型
export interface AreaNodeRespVO {
  id: number
  name: string
  children?: AreaNodeRespVO[]
}

// 获得地区树
export const getAreaTree = async () => {
  return await request.get({ url: '/system/area/tree' })
}

// 获得 IP 对应的地区名
export const getAreaByIp = async (ip: string) => {
  return await request.get({ url: '/system/area/get-by-ip?ip=' + ip })
}
