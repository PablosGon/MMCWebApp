import { ClubEventRegistry } from "./club-event-registry.model";
import { StarBadgeCase } from "./star-badge-case.model";
import { TrophyRegistry } from "./trophy-registry.model";

export interface Player {
    tag: string,
    name: string,
    clubName: string,
    clubTag: string,
    iconId: number,
    trophies: number,
    higherstTrophies: number,
    nameColor: string,
    trophyRegistries: TrophyRegistry[],
    seasonTrophyProgress: number[],
    badges: StarBadgeCase,
    clubEventRegistries: ClubEventRegistry[]
}