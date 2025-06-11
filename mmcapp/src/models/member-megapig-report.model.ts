import { MegapigStatus } from "@/enums/megapig-status.enum";

export interface MemberMegapigReport {
  playerTag: string,
  status: MegapigStatus,
}