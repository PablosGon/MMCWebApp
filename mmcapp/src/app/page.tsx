import { CLUBS } from "@/constants/clubs-names.constant";
import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <ul className="flex flex-wrap gap-10 justify-center pt-20">
      {
        CLUBS.map((club, index) => (
          <li key={index}>
            <Link href={"/club/" + index}>
              <div className="w-75 h-75 bg-gray-800 rounded-4xl text-center flex flex-col justify-center p-5">
                <Image src={"/clubs/" + index + ".png"} alt={club.name} width={500} height={500}/>
                <h2 className="text-2xl">{club.name}</h2>
              </div>
            </Link>
          </li>
        ))
      }
    </ul>
  );
}
