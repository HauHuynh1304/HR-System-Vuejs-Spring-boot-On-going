const passwordRegexExp = new RegExp(
  /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$/
);

export function isValidPassword(string) {
  return passwordRegexExp.test(string);
}
