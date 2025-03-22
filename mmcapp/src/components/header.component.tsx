import Link from "next/link";

export default function Header() {
    return (
        <header className="h-30 bg-gradient-to-r from-purple-800 to-purple-950">
            <Link href="/">
                <img src="/AppLogo_Beta.png"  className="max-w-80"/>
            </Link>
        </header>
    );
}