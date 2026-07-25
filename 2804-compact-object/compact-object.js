/**
 * @param {Object|Array} obj
 * @return {Object|Array}
 */
var compactObject = function(obj) {
    if (obj === null || typeof obj !== "object") {
        return obj;
    }

    if (Array.isArray(obj)) {
        return obj
            .filter(Boolean)
            .map(item => compactObject(item));
    }

    const result = {};

    for (const key in obj) {
        const value = compactObject(obj[key]);
        if (Boolean(value)) {
            result[key] = value;
        }
    }

    return result;
};