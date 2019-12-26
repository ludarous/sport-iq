import {Component, OnInit} from '@angular/core';
import {TreeNode} from 'primeng/api';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {IActivityCategory} from '../../../entities/model/activity-category.model';
import {ActivityCategoryService} from '../../../services/rest/activity-category.service';
import {ToastService} from '../../../../modules/core/services/message.service';

@Component({
    selector: 'app-activity-categories-list',
    templateUrl: './activity-categories-list.component.html',
    styleUrls: ['./activity-categories-list.component.scss']
})
export class ActivityCategoriesListComponent implements OnInit {

    tableCols: Array<any>;
    categories: Array<IActivityCategory>;
    mainCategories: Array<IActivityCategory>;

    categoriesNodes: Array<TreeNode>;

    constructor(private activityCategoryService: ActivityCategoryService,
                private toastService: ToastService) {
    }

    ngOnInit() {

        this.tableCols = [
            {field: 'name', header: 'Název'},
            {field: 'description', header: 'Popis'},
        ];

        this.load();

    }

    load() {
        const getCategories$ = this.activityCategoryService.query({
            page: 0,
            size: 1000,
        });

        getCategories$.subscribe((categories: HttpResponse<Array<IActivityCategory>>) => {
            this.categories = categories.body;
            this.mainCategories = this.categories.filter(c => c.parentActivityCategoryId == null);

            this.categoriesNodes = new Array<TreeNode>();
            for (const category of this.mainCategories) {
                this.categoriesNodes.push(this.createCategoryNode(category));
            }
            console.log(this.categoriesNodes);
        });
    }

    createCategoryNode(category: IActivityCategory): TreeNode {
        const childActivityCategories = Array<TreeNode>();
        if (category.childActivityCategories && category.childActivityCategories.length > 0) {
            for (const child of category.childActivityCategories) {
                childActivityCategories.push(this.createCategoryNode(child));
            }
        }

        const treeNode = {
            data: category,
            children: childActivityCategories,
            leaf: !(childActivityCategories && childActivityCategories.length > 0),
            expanded: (childActivityCategories && childActivityCategories.length > 0)
        };

        return treeNode;
    }

    delete(event, category: IActivityCategory) {
        event.stopPropagation();

        if (confirm('Opravdu chceš smazat categorii ' + category.name)) {
            this.activityCategoryService.remove(category.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Kategorii se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
