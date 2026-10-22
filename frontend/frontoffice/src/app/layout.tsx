import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Lokaly — Produits agricoles malgaches",
  description: "Commandez des produits agricoles locaux directement auprès des fournisseurs.",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="fr">
      <body>{children}</body>
    </html>
  );
}
