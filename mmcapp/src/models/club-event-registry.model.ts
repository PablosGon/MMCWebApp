import { MegapigStatus } from "@/enums/megapig-status.enum";

export interface ClubEventRegistry {
  status: MegapigStatus,
  dateTime: string,
}