import { Season } from "@/models/season.model";
import { ClubStarBoardComponent } from "./club-star-board.component";

export function SeasonStarBoardComponent(params: {season: Season}) {

    const season = params.season;

    console.log(season);

    return (
        <div className="flex flex-col gap-5">
            <h1 className="text-4xl">
                Temporada {season.id}
            </h1>
            <ul className="flex flex-col gap-5">
                {
                    season.starPlayersByClub.map((club, index) => (
                        <li key={index}>
                            <ClubStarBoardComponent club={club} index={index}/>
                        </li>
                    ))
                }
            </ul>
        </div>

    )
}