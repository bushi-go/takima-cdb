import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'none'
})
export class NonePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value;
  }

}
