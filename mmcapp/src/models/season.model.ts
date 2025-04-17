import { ClubStarPlayerRegistry } from "./club-star-player-registry.model";

export interface Season {
    id: number,
    starPlayersByClub: ClubStarPlayerRegistry[],
}