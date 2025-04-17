import { StarPlayer } from "./star-player.model";

export interface StarWeekPlayer extends StarPlayer {
    week: number,
    trophies: number,
}