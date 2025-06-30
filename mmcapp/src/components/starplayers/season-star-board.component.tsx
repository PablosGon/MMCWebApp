import { Season } from "@/models/season.model";
import { ClubStarBoardComponent } from "./club-star-board.component";
import { CLUBS } from "@/constants/clubs-names.constant";

export function SeasonStarBoardComponent(params: Readonly<{season: Season}>) {

    const season = params.season;

    return (
        <div className="flex flex-col gap-5">
            <h1 className="text-4xl">
                Temporada {season.id}
            </h1>
            <ul className="flex flex-col gap-5">
                {
                    season.starPlayersByClub.map((club, index) => (
                        <li key={CLUBS[index].id}>
                            <ClubStarBoardComponent club={club} index={index}/>
                        </li>
                    ))
                }
            </ul>
        </div>

    )
}