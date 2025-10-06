import { Suspense } from "react";
import { getFilesNames } from "./api";
import DataRender from "./data-render";

export default async function Page() {
  const files = await getFilesNames().catch(() => []);

  return (
    <main className="relative flex flex-col items-center justify-center min-h-screen bg-white text-black">
      <Suspense fallback={<p className="text-gray-500">Loading files...</p>}>
        <DataRender initialFiles={files} />
      </Suspense>
    </main>
  );
}
