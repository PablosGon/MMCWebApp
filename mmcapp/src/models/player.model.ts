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
}