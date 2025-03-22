import Link from "next/link";
import Image from "next/image";

export default function Header() {
    return (
        <header className="h-30 bg-gradient-to-r from-purple-800 to-purple-950">
            <Link href="/">
                <Image src="/AppLogo_Beta.png" alt="App Logo Beta" height={500} width={500} className="max-w-80"/>
            </Link>
        </header>
    );
}