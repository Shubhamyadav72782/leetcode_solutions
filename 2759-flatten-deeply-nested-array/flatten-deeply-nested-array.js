/**
 * @param {Array} arr
 * @param {number} depth
 * @return {Array}
 */
var flat = function(arr, depth) {
    if (depth === 0) return arr;

    let result = [];

    for (const item of arr) {
        if (Array.isArray(item) && depth > 0) {
            result.push(...flat(item, depth - 1));
        } else {
            result.push(item);
        }
    }

    return result;
};