"use client";

import { useEffect, useState } from "react";
import { uploadCSVFile, getFilesNames } from "./api";

export default function DataRender({ initialFiles }: { initialFiles: string[] }) {
    const [uploadedFiles, setUploadedFiles] = useState<string[]>(initialFiles);
    const [loading, setLoading] = useState(false);

    const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        if (!e.target.files) return;
        const selectedFiles = Array.from(e.target.files);
        setLoading(true);

        try {
            await uploadCSVFile(selectedFiles);
            const refreshed = await getFilesNames();
            setUploadedFiles(refreshed);
        } catch (err) {
            console.error(err);
            alert("Upload failed");
        } finally {
            setLoading(false);
            e.target.value = "";
        }
    };

    useEffect(() => {
        async function refreshFiles() {
            const refreshed = await getFilesNames().catch(() => []);
            setUploadedFiles(refreshed);
        }
        refreshFiles();
    }, []);

    return (
        <section className="flex flex-col items-center justify-center w-full relative">
            {/* upload button  */}
            <div className="absolute top-6 right-6">
                <label
                    htmlFor="fileUpload"
                    className={`bg-black text-white px-4 py-2 rounded-md cursor-pointer transition ${loading ? "opacity-50 cursor-not-allowed" : "hover:bg-gray-800"
                        }`}
                >
                    {loading ? "Uploading..." : "Upload CSV"}
                </label>
                <input
                    id="fileUpload"
                    type="file"
                    accept=".csv"
                    multiple
                    className="hidden"
                    disabled={loading}
                    onChange={handleFileChange}
                />
            </div>

            {/* files box */}
            <div className="border-2 border-black rounded-lg w-96 p-6 text-center shadow-sm">
                {uploadedFiles.length === 0 ? (
                    <p className="text-gray-500">No files yet</p>
                ) : (
                    <ul className="divide-y divide-gray-300">
                        {uploadedFiles.map((file, index) => (
                            <li
                                key={index}
                                className="py-2 text-sm font-medium break-all hover:bg-gray-50 transition"
                            >
                                {file}
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </section>
    );
}
