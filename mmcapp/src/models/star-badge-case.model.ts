import { StarLegend } from "./star-legend.model";
import { StarMaster } from "./star-master.model";
import { StarSeasonPlayer } from "./star-season-player.model";
import { StarWeekPlayer } from "./star-week-player.model";

export interface StarBadgeCase {
    starPlayerBadges: StarWeekPlayer[],
    grandStarPlayerBadges: StarSeasonPlayer[],
    starLegendBadges: StarLegend[],
    starMasterBadges: StarMaster[],
}