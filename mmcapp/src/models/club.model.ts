import { ClubMember } from "./club-member.model";

export interface Club {
    tag: string,
    name: string,
    description: string,
    trophies: number,
    requiredTrophies: number,
    members: ClubMember[],
    badgeId: number,
}