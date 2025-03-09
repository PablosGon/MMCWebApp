import { PlayerIcon } from "./player-icon.model";

export interface ClubMember {
    icon: PlayerIcon,
    tag: string,
    name: string,
    trophies: number,
    role:string,
}