
const formatMoney = (value: number | undefined) => {
    return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(value || 0);
}

export const PRODUCT_RESPONSIVE_CLASS = {
    five: 'col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2',
    four: 'col-12 col-sm-6 col-md-4 col-lg-3 col-xl-3',
    three: 'col-12 col-sm-6 col-md-4 col-lg-4 col-xl-4',
    two: 'col-12 col-sm-6 col-md-6 col-lg-6 col-xl-6',
    one: 'col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12',
}

export const hexToBase64 = (str: string | undefined) => {
   if (str) {
       return btoa(String.fromCharCode.apply(null, str.split('').map((c: string) => c.charCodeAt(0))));
   }
   return '';
}

export const tinyMCEConfig = {
    menubar: false,
    toolbar: 'undo redo | formatselect | bold italic underline | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat',
    plugins: 'lists',
    toolbar_drawer: 'floating',
    content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }',
    height: 300,
    setup: (editor: any) => {
        editor.on('init', () => {
            editor.setContent('');
        });
    }
}
export default formatMoney;