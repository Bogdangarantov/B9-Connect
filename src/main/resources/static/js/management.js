let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user: null,
        cookieValue: "",
        loading: false,
        newUser: {
            name: "",
            login: "",
            password: "",
            email: "",
            roles: []
        },
        blockUser:{
            id:""
        },
        unBlockUser:{
            id:""
        },
        newService: {
            name: "",
            description: "",
            faq: [
                {
                    question: "",
                    answer: ""
                }
            ],
            users: [],
            contactEmail:""
        },
        blockUserIndex: 0,
        allUsers: []
    },
    methods: {
        async getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
        },
        getUser() {
            let th = this
            let user = JSON.parse(document.getElementById("user").value)
            console.log(user)
            th.user = user
            user.value = ""
        },
        async getAllUsers() {
            let response = await fetch('/api/v1/users', {
                method: 'GET',
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok) {
                await this.stopLoading()
                this.allUsers = JSON.parse(await response.text())
                console.log(this.allUsers)
                await this.multiSelectStart()
                await this.multiselectDropdown(window.MultiselectDropdownOptions,"multiple");
            }


        },
        async addUser() {
            let th = this
            let response = await fetch("/api/v1/users", {
                method: "POST",
                headers: {
                    'Content-type': "application/json",
                    'X-XSRF-TOKEN': this.cookieValue
                },
                body: JSON.stringify(th.newUser)
            })
        },
        async logout() {
            let response = await fetch('/logout', {
                method: 'POST',
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok) {
                await location.reload()
            }
        },
        addQuestion() {
            let faq = this.newService.faq[this.newService.faq.length - 1]
            if (faq.question.length !== 0 && faq.answer.length !== 0) {
                this.newService.faq.push({
                    question: "",
                    answer: ""
                })
            } else {
                alert("You didn`t fill out the previous faq form")
            }

        },
        removeQuestion(faq) {
            this.newService.faq = this.newService.faq.filter(faq_ => faq_.question !== faq.question && faq_.answer !== faq.answer)
        },
        async blockUser() {

        },
        async startLoading() {
            this.loading = true
        },
        async stopLoading() {
            this.loading = false
        },
        async createService(e){
            e.preventDefault()
            console.log(this.newService)
            let response = await fetch('/api/v1/services/', {
                method: 'POST',
                headers: {
                    "Content-Type":"application/json",
                    'X-XSRF-TOKEN': this.cookieValue
                },
                body:JSON.stringify(this.newService)
            })
            if (response.ok) {
                await location.reload()
            }
        },
        async multiSelectStart() {
            var style = document.createElement('style');
            style.setAttribute("id", "multiselect_dropdown_styles");
            style.innerHTML = `
        .multiselect-dropdown{
          display: inline-block;
          padding: 2px 5px 0px 5px;
          border-radius: 4px;
          border: solid 1px #ced4da;
          background-color: white;
          position: relative;
          min-height:2rem;
          background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3e%3cpath fill='none' stroke='%23343a40' stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M2 5l6 6 6-6'/%3e%3c/svg%3e");
          background-repeat: no-repeat;
          background-position: right .75rem center;
          background-size: 16px 12px;
        }
        .multiselect-dropdown span.optext, .multiselect-dropdown span.placeholder{
          margin-right:0.5em; 
          margin-bottom:2px;
          padding:1px 0; 
          border-radius: 4px; 
          display:inline-block;
        }
        .multiselect-dropdown span.optext{
          background-color:lightgray;
          padding:1px 0.75em; 
        }
        .multiselect-dropdown span.optext .optdel {
          float: right;
          margin: 0 -6px 1px 5px;
          font-size: 0.7em;
          margin-top: 2px;
          cursor: pointer;
          color: #666;
        }
        .multiselect-dropdown span.optext .optdel:hover { color: #c66;}
        .multiselect-dropdown span.placeholder{
          color:#ced4da;
        }
        .multiselect-dropdown-list-wrapper{
          box-shadow: gray 0 3px 8px;
          z-index: 100;
          padding:2px;
          border-radius: 4px;
          border: solid 1px #ced4da;
          display: none;
          margin: -1px;
          position: absolute;
          top:0;
          left: 0;
          right: 0;
          background: white;
        }
        .multiselect-dropdown-list-wrapper .multiselect-dropdown-search{
          margin-bottom:5px;
        }
        .multiselect-dropdown-list{
          padding:2px;
          height: 15rem;
          overflow-y:auto;
          overflow-x: hidden;
        }
        .multiselect-dropdown-list::-webkit-scrollbar {
          width: 6px;
        }
        .multiselect-dropdown-list::-webkit-scrollbar-thumb {
          background-color: #bec4ca;
          border-radius:3px;
        }
        
        .multiselect-dropdown-list div{
          padding: 5px;
        }
        .multiselect-dropdown-list input{
          height: 1.15em;
          width: 1.15em;
          margin-right: 0.35em;  
        }
        .multiselect-dropdown-list div.checked{
        }
        .multiselect-dropdown-list div:hover{
          background-color: #ced4da;
        }
        .multiselect-dropdown span.maxselected {width:100%;}
        .multiselect-dropdown-all-selector {border-bottom:solid 1px #999;}
        `;
            document.head.appendChild(style);
        },
        async multiselectDropdown(options,id) {
            var config = {
                search: true,
                height: 'fit-content',
                placeholder: 'select',
                txtSelected: 'selected',
                txtAll: 'All',
                txtRemove: 'Remove',
                txtSearch: 'search',
                ...options
            };

            function newEl(tag, attrs) {
                var e = document.createElement(tag);
                if (attrs !== undefined) Object.keys(attrs).forEach(k => {
                    if (k === 'class') {
                        Array.isArray(attrs[k]) ? attrs[k].forEach(o => o !== '' ? e.classList.add(o) : 0) : (attrs[k] !== '' ? e.classList.add(attrs[k]) : 0)
                    } else if (k === 'style') {
                        Object.keys(attrs[k]).forEach(ks => {
                            e.style[ks] = attrs[k][ks];
                        });
                    } else if (k === 'text') {
                        attrs[k] === '' ? e.innerHTML = '&nbsp;' : e.innerText = attrs[k]
                    } else e[k] = attrs[k];
                });
                return e;
            }


            document.querySelectorAll("#"+id).forEach((el, k) => {

                var div = newEl('div', {
                    class: 'multiselect-dropdown',
                    style: {width: "40rem", padding: config.style?.padding ?? ''}
                });
                el.style.display = 'none';
                el.parentNode.insertBefore(div, el.nextSibling);
                var listWrap = newEl('div', {class: 'multiselect-dropdown-list-wrapper'});
                var list = newEl('div', {class: 'multiselect-dropdown-list', style: {height: config.height}});
                var search = newEl('input', {
                    class: ['multiselect-dropdown-search'].concat([config.searchInput?.class ?? 'form-control']),
                    style: {
                        width: '100%',
                        display: el.attributes['multiselect-search']?.value === 'true' ? 'block' : 'none'
                    },
                    placeholder: config.txtSearch
                });
                listWrap.appendChild(search);
                div.appendChild(listWrap);
                listWrap.appendChild(list);

                el.loadOptions = () => {
                    list.innerHTML = '';

                    if (el.attributes['multiselect-select-all']?.value == 'true') {
                        var op = newEl('div', {class: 'multiselect-dropdown-all-selector'})
                        var ic = newEl('input', {type: 'checkbox'});
                        op.appendChild(ic);
                        op.appendChild(newEl('label', {text: config.txtAll}));

                        op.addEventListener('click', () => {
                            op.classList.toggle('checked');
                            op.querySelector("input").checked = !op.querySelector("input").checked;

                            var ch = op.querySelector("input").checked;
                            list.querySelectorAll(":scope > div:not(.multiselect-dropdown-all-selector)")
                                .forEach(i => {
                                    if (i.style.display !== 'none') {
                                        i.querySelector("input").checked = ch;
                                        i.optEl.selected = ch
                                    }
                                });

                            el.dispatchEvent(new Event('change'));
                        });
                        ic.addEventListener('click', (ev) => {
                            ic.checked = !ic.checked;
                        });
                        el.addEventListener('change', (ev) => {
                            let itms = Array.from(list.querySelectorAll(":scope > div:not(.multiselect-dropdown-all-selector)")).filter(e => e.style.display !== 'none')
                            let existsNotSelected = itms.find(i => !i.querySelector("input").checked);
                            if (ic.checked && existsNotSelected) ic.checked = false;
                            else if (ic.checked == false && existsNotSelected === undefined) ic.checked = true;
                        });

                        list.appendChild(op);
                    }

                    Array.from(el.options).map(o => {
                        var op = newEl('div', {class: o.selected ? 'checked' : '', optEl: o})
                        var ic = newEl('input', {type: 'checkbox', checked: o.selected});
                        op.appendChild(ic);
                        op.appendChild(newEl('label', {text: o.text}));

                        op.addEventListener('click', () => {
                            op.classList.toggle('checked');
                            op.querySelector("input").checked = !op.querySelector("input").checked;
                            op.optEl.selected = !!!op.optEl.selected;
                            el.dispatchEvent(new Event('change'));
                        });
                        ic.addEventListener('click', (ev) => {
                            ic.checked = !ic.checked;
                        });
                        o.listitemEl = op;
                        list.appendChild(op);
                    });
                    div.listEl = listWrap;

                    div.refresh = () => {
                        div.querySelectorAll('span.optext, span.placeholder').forEach(t => div.removeChild(t));
                        var sels = Array.from(el.selectedOptions);
                        if (sels.length > (el.attributes['multiselect-max-items']?.value ?? 5)) {
                            div.appendChild(newEl('span', {
                                class: ['optext', 'maxselected'],
                                text: sels.length + ' ' + config.txtSelected
                            }));
                        } else {
                            sels.map(x => {
                                var c = newEl('span', {class: 'optext', text: x.text, srcOption: x});
                                if ((el.attributes['multiselect-hide-x']?.value !== 'true'))
                                    c.appendChild(newEl('span', {
                                        class: 'optdel',
                                        text: '🗙',
                                        title: config.txtRemove,
                                        onclick: (ev) => {
                                            c.srcOption.listitemEl.dispatchEvent(new Event('click'));
                                            div.refresh();
                                            ev.stopPropagation();
                                        }
                                    }));

                                div.appendChild(c);
                            });
                        }
                        if (0 == el.selectedOptions.length) div.appendChild(newEl('span', {
                            class: 'placeholder',
                            text: el.attributes['placeholder']?.value ?? config.placeholder
                        }));
                    };
                    div.refresh();
                }
                el.loadOptions();

                search.addEventListener('input', () => {
                    list.querySelectorAll(":scope div:not(.multiselect-dropdown-all-selector)").forEach(d => {
                        var txt = d.querySelector("label").innerText.toUpperCase();
                        d.style.display = txt.includes(search.value.toUpperCase()) ? 'block' : 'none';
                    });
                });

                div.addEventListener('click', () => {
                    div.listEl.style.display = 'block';
                    search.focus();
                    search.select();
                });

                document.addEventListener('click', function (event) {
                    if (!div.contains(event.target)) {
                        listWrap.style.display = 'none';
                        div.refresh();
                    }
                });
            });
        },
    },
    async created() {
        await this.startLoading()
        this.getUser()
        await this.getAllUsers()
        await this.stopLoading()
        this.cookieValue = await this.getCookie('XSRF-TOKEN')
    }
})
