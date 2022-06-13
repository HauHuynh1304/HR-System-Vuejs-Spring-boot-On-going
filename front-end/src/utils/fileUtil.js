export async function createImageFromUrl(url) {
  const response = await fetch(url);
  const blob = await response.blob();
  return new File([blob], "image.jpg", {
    type: blob.type,
  });
}
