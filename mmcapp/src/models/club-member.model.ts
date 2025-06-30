import { MegapigStatus } from "@/enums/megapig-status.enum";
import { StarBadgeCase } from "./star-badge-case.model";

export interface ClubMember {
    iconId: number,
    tag: string,
    name: string,
    trophies: number,
    role: string,
    lastRegistry: number,
    starBadgeCase: StarBadgeCase,
    lastMegapigs: MegapigStatus[],
    firstSeason: number,
}