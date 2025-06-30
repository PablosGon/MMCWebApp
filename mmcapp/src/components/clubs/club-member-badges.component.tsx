import { StarBadgeCase } from "@/models/star-badge-case.model";
import Image from "next/image";

export function ClubMemberBadges(params: Readonly<{starBadgeCase: StarBadgeCase, clubId: number}>) {

    const badges = params.starBadgeCase;
    const clubId = params.clubId;

    return (
        <ul className="flex items-center gap-2">
            <li className="flex items-center gap-1">
                <Image src="/star_players/grand_star_player.png" width={20} height={20} alt="Grand star player badge"/>
                <p className="text-sm">{badges.grandStarPlayerBadges.filter(x => x.clubId === clubId).length}
                    {
                        badges.grandStarPlayerBadges.filter(x => x.clubId === clubId).length !== badges.grandStarPlayerBadges.length ?
                            `(${badges.grandStarPlayerBadges.length})` : ''
                    } 
                </p>
            </li>
            <li className="flex items-center gap-1">
                <Image src="/star_players/star_legend.png" width={20} height={20} alt="Star legend badge"/>
                <p className="text-sm">{badges.starLegendBadges.filter(x => x.clubId === clubId).length}
                    {
                        badges.starLegendBadges.filter(x => x.clubId === clubId).length !== badges.starLegendBadges.length ?
                            `(${badges.starLegendBadges.length})` : ''
                    } 
                </p>
            </li>
            <li className="flex items-center gap-1">
                <Image src="/star_players/star_master.png" width={20} height={20} alt="Star master badge"/>
                <p className="text-sm">{badges.starMasterBadges.filter(x => x.clubId === clubId).length}
                    {
                        badges.starMasterBadges.filter(x => x.clubId === clubId).length !== badges.starMasterBadges.length ?
                            `(${badges.starMasterBadges.length})` : ''
                    } 
                </p>
            </li>
            <li className="flex items-center gap-1">
                <Image src="/star_players/star_player.png" width={20} height={20} alt="Star player badge"/>
                <p className="text-sm">{badges.starPlayerBadges.filter(x => x.clubId === clubId).length}
                    {
                        badges.starPlayerBadges.filter(x => x.clubId === clubId).length !== badges.starPlayerBadges.length ?
                            `(${badges.starPlayerBadges.length})` : ''
                    } 
                </p>
            </li>
        </ul>
    );
}