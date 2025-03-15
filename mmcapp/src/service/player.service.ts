import { Player } from "@/models/player.model";

export async function getPlayer(playerTag: string): Promise<Player> {
    const response = await fetch(`http://localhost:8080/player/${playerTag}`);

    if (!response.ok) {
        throw new Error(`API Error: ${response.status}`);
    }
    
    return response.json() as Promise<Player>;
}