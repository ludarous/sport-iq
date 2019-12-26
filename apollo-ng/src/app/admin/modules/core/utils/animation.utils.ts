import * as $ from 'jquery';

export class AnimationUtils {

  static scrollToSelector(selector: string) {
    const $scrollTo = $(selector);
    this.scrollToElement($scrollTo);
  }

  static scrollToElement($scrollTo: JQuery<any>) {
    const $container = $('html, body');

    if ($container.length > 0 && $scrollTo.length > 0) {
      $container.animate({scrollTop: $scrollTo.offset().top - $container.offset().top, scrollLeft: 0}, 300);
    }
  }
}
