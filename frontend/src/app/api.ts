"use server";

const baseUrl = process.env.NEXT_PUBLIC_API_URL;

export async function uploadCSVFile(files: File[]): Promise<void> {
    const formData = new FormData();
    files.forEach((file) => formData.append("files", file));

    const response = await fetch(`${baseUrl}/api/upload`, {
        method: "POST",
        body: formData,
    });

    if (!response.ok) {
        const text = await response.text();
        throw new Error(`Upload failed: ${text}`);
    }
}

export async function getFilesNames(): Promise<string[]> {
    const response = await fetch(`${baseUrl}/api/files`);
    if (!response.ok) throw new Error("Failed to fetch files info");
    return response.json();
}