export class ViewUtils {
  static isAnyPartOfElementInViewport(el): boolean {

    if (el) {
      const rect = el.getBoundingClientRect();
      // DOMRect { x: 8, y: 8, width: 100, height: 100, top: 8, right: 108, bottom: 108, left: 8 }
      const windowHeight = (window.innerHeight || document.documentElement.clientHeight);
      const windowWidth = (window.innerWidth || document.documentElement.clientWidth);

      // http://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
      const vertInView = (rect.top <= windowHeight) && ((rect.top + rect.height) >= 0);
      const horInView = (rect.left <= windowWidth) && ((rect.left + rect.width) >= 0);

      return (vertInView && horInView);
    }
    return true;
  }

  static isTopPartOfElementInViewport(el): boolean {

    if (el) {
      const rect = el.getBoundingClientRect();
      return rect.top > 0;
    }
    return true;
  }
}
