import { MegapigStatusColors } from "@/constants/megapig-status-colors.constant";
import { MegapigStatus } from "@/enums/megapig-status.enum";
import { ClubEventRegistry } from "@/models/club-event-registry.model";
import { Player } from "@/models/player.model";

export function MegapigRegistriesComponent(params: Readonly<{player: Player}>) {

  const player = params.player;

  return (
    <div className="flex flex-col gap-5 items-center">
      <aside>
        <ul className="flex flex-wrap gap-3 gap-y-0">
            {
                MegapigStatusColors.map((status: { status: MegapigStatus, color: string, text: string }) => (
                    <li key={status.status} className="flex flex-row items-center gap-1">
                        <div
                            className={`
                                w-3 h-3 rounded-sm
                                ${status.color}
                                peer-checked:opacity-100
                                transition-colors
                            `}
                        ></div>
                        <span>{status.text}</span>
                    </li>
                ))
            }
        </ul>
    </aside>
    {
      <ul className="flex flex-wrap gap-3">
        {
            player.clubEventRegistries.map((cer: ClubEventRegistry, index) => (
                <li key={player.tag + index} className="flex gap-1 items-center">
                    <div
                        className={`
                            w-10 h-10 rounded-sm
                            ${MegapigStatusColors[cer.status].color}
                            peer-checked:opacity-100
                            transition-colors
                        `}
                    ></div>
                    <p>
                        {new Date(cer.dateTime).toLocaleDateString()}
                    </p>
                </li>
            ))
            
        }
      </ul>
    }
    </div>
  )

}