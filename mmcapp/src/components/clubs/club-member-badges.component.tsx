import { StarBadgeCase } from "@/models/star-badge-case.model";
import Image from "next/image";

export function ClubMemberBadges(params: Readonly<{starBadgeCase: StarBadgeCase}>) {

    const badges = params.starBadgeCase;

    return (
        <ul className="flex items-center gap-2">
            <li className="flex items-center gap-1">
                <Image src="/star_players/grand_star_player.png" width={20} height={20} alt="Grand star player badge"/>
                <p className="text-sm">{badges.grandStarPlayerBadges.length}</p>
            </li>
            <li className="flex items-center gap-1">
                <Image src="/star_players/star_legend.png" width={20} height={20} alt="Star legend badge"/>
                <p className="text-sm">{badges.starLegendBadges.length}</p>
            </li>
            <li className="flex items-center gap-1">
                <Image src="/star_players/star_master.png" width={20} height={20} alt="Star master badge"/>
                <p className="text-sm">{badges.starMasterBadges.length}</p>
            </li>
            <li className="flex items-center gap-1">
                <Image src="/star_players/star_player.png" width={20} height={20} alt="Star player badge"/>
                <p className="text-sm">{badges.starPlayerBadges.length}</p>
            </li>
        </ul>
    );
}