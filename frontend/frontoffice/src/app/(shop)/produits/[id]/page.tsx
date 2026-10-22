export default function ProduitDetailPage({ params }: { params: { id: string } }) {
  return (
    <main>
      <h1>Produit #{params.id}</h1>
    </main>
  );
}
