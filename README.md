# RecyclerAdapter
Adapter for RecyclerView

## How to 
To get a Git project into your build:

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

``` groovy

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```

### Step 2. Add the dependency
Tag : [![](https://jitpack.io/v/XingRay/RecyclerAdapter.svg)](https://jitpack.io/#XingRay/RecyclerAdapter)
``` groovy

	dependencies {
	        implementation 'com.github.XingRay:RecyclerAdapter:Tag'
	}

```

### step 3. code

```kotlin

mAdapter = RecyclerAdapter<TestData, TestViewHolder>(applicationContext)
		.itemLayoutId(R.layout.item_recycler_view_test_list)
		.viewHolderFactory { itemView, _ -> TestViewHolder(itemView) }
		.itemClickListener { _, position, t ->
			showToast("$position ${t?.name} clicked")
		}

rvList.adapter = mAdapter

```

```java

RecyclerView rvList = findViewById(R.id.rv_list);

rvList.setLayoutManager(new LinearLayoutManager(mContext));

mAdapter = new RecyclerAdapter<Banner, BannerViewHolder>(mContext)
		.itemLayoutId(R.layout.view_banner_list_item)
		.viewHolderFactory(new BaseViewHolderFactory<BannerViewHolder>() {
			@Override
			public BannerViewHolder createViewHolder(View itemView, int viewType) {
				return new BannerViewHolder(itemView);
			}
		})
		.itemClickListener(new OnItemClickListener<Banner>() {
			@Override
			public void onItemClick(ViewGroup parent, int position, Banner banner) {
				onBannerClick(position, banner);
			}
		});

rvList.setAdapter(mAdapter);

```

```java

private void showBanners(List<Banner> banners) {
    mAdapter.update(banners);
}

```

more usage see sample
