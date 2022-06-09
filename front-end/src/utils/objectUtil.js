export function resetObject(object) {
  Object.keys(object).forEach((i) => (object[i] = null));
}
