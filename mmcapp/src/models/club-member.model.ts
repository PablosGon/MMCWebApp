import { StarBadgeCase } from "./star-badge-case.model";

export interface ClubMember {
    iconId: number,
    tag: string,
    name: string,
    trophies: number,
    role: string,
    lastRegistry: number,
    starBadgeCase: StarBadgeCase,
}