import { StarLegend } from "./star-legend.model";
import { StarMaster } from "./star-master.model";
import { StarSeasonPlayer } from "./star-season-player.model";
import { StarWeekPlayer } from "./star-week-player.model";

export interface ClubStarPlayerRegistry {
    weeklyStarPlayers: StarWeekPlayer[],
    grandStarPlayer: StarSeasonPlayer,
    starLegend: StarLegend,
    starMaster: StarMaster,
}