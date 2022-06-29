export function resetObject(object) {
  Object.keys(object).forEach((i) => (object[i] = null));
}

/*
 * @Param Object
 * Check object not null at all properties
 */
export function isContainNullValue(object) {
  let arrValue = Object.values(object);
  if (arrValue.includes(null) || arrValue.includes("")) {
    return true;
  }
  return false;
}

export function isAllNullValue(object) {
  let arrValue = Object.values(object);
  let countNonNull = 0;
  arrValue.forEach((el) => {
    if (el !== null && el !== "") {
      countNonNull++;
    }
  });
  if (countNonNull > 0) {
    return false;
  }
  return true;
}

export function diff(obj1, obj2) {
  Object.keys(obj1).forEach((key) => {
    if (obj1[key] === obj2[key]) {
      obj1[key] = null;
    }
  });
}

export function isContainNullExceptProps(obj, ...props) {
  let copyObj = Object.assign({}, obj);
  props.forEach((el) => {
    delete copyObj[el];
  });
  return isContainNullValue(copyObj);
}
